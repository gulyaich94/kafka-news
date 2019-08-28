import React from 'react';
import axios from 'axios';
import Paper from '@material-ui/core/Paper';
import Title from '../Title';
import Grid from '@material-ui/core/Grid';
import { Button } from '@material-ui/core';
import { Link as RouterLink } from 'react-router-dom';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import Swal from 'sweetalert2'
import MyContext from '../MyContext.js';

export class LoginPage extends React.Component {
    state = {
        isValid: false,
        user: {
            loginOrEmail: '',
            password: '',
        },
        formErrors: {
            loginOrEmail: false,
            password: false,
        },
    }

    handleChange = (event) => {
        const fieldName = event.target.name;
        const filedValue = event.target.value;
        this.setState(prevState => ({
            user: {                   
                ...prevState.user,    
                [fieldName]: filedValue
            }
        }), () => this.validateForm());
    }

    validateForm = () => {
        this.refreshFormError();
        let isValidByRequired = this.validateRequiredFields();

        this.setState({ isValid: isValidByRequired });
    }

    refreshFormError = () => {
        for (let key in this.state.formErrors) {
            this.setState(prevState => ({
                formErrors: {                   
                    ...prevState.formErrors,    
                    [key]: false
                }
            }));
        }
    }

    validateRequiredFields = () => {
        const { loginOrEmail, password } = this.state.user;
        const requiredFields = { [loginOrEmail]: loginOrEmail, [password]: password };

        let isValid = true;
        for (let key in requiredFields) {
            if (requiredFields[key] == null || requiredFields[key].trim().length === 0) {
                isValid = false;
            } else {
                if (requiredFields[key].trim().length < 1) {
                    isValid = false;
                    this.setState(prevState => ({
                        formErrors: {                   
                            ...prevState.formErrors,    
                            [key]: true
                        }
                    }));
                }
            }
        }
        return isValid;

    }


    handleSubmit = (event) => {
        event.preventDefault();
        const user = this.state.user;
        axios.post('http://localhost:8082/api/auth/login', 
            user
        )
            .then((response) => {
                console.log(response);
                const newtoken = {
                    accessToken: response.data.jwtTokenResponse.accessToken,
                    tokenType: response.data.jwtTokenResponse.tokenType
                };
                this.context.updateToken(newtoken);
                
                Swal.fire(
                    'Успешно!',
                    'Вы авторизованы',
                    'success'
                  )
                this.props.history.push(`/`);
            })
            .catch((error) => {
                console.log(error);
                Swal.fire(
                    'Ошибка!',
                    error.response.data.message,
                    'error'
                  )
            });
    }

    render() {
        return (
            <>
                <Paper className="typePaper">
                    <Title>Логин</Title>
                    <form className={"d-flex f-wrap typeForm"} autoComplete="off" onSubmit={this.handleSubmit}>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="loginOrEmail">Логин или email*</InputLabel>
                            <Input id="loginOrEmail" name="loginOrEmail" value={this.state.user.loginOrEmail} onChange={this.handleChange} required/>
                        </FormControl>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="password">Пароль *</InputLabel>
                            <Input id="password" name="password" value={this.state.user.password} onChange={this.handleChange} minLength="6" maxLength="20" required/>
                        </FormControl>
                        <Grid item xs={12} className="d-flex justify-end">
                            <Button type="submit" variant="contained" color="primary" className="typeBtn" disabled={!this.state.isValid}>
                                Войти
                            </Button>
                            <Button variant="contained" color="secondary" className="typeBtn" component={RouterLink} to="/">
                                Отмена
                            </Button>
                        </Grid>
                    </form>
                </Paper>
            </>
        );
    }
}

LoginPage.contextType = MyContext;

