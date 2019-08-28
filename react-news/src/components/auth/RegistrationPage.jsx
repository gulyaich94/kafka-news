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

export class RegistrationPage extends React.Component {
    state = {
        isValid: false,
        user: {
            fullname: '',
            login: '',
            email: '',
            password: '',
        },
        formErrors: {
            fullname: false,
            login: false,
            email: false,
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
        const { login, password } = this.state.user;
        const requiredFields = { [login]: login, [password]: password };

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
        axios.post('http://localhost:8082/api/auth/signup', 
            user
        )
            .then((response) => {
                console.log(response);
                Swal.fire(
                    'Успешно!',
                    'Вы зарегистрированы',
                    'success'
                  )
                this.props.history.push(`/login/`);
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
                    <Title>Зарегистрироваться</Title>
                    <form className={"d-flex f-wrap typeForm"} autoComplete="off" onSubmit={this.handleSubmit}>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="fullname" error={this.state.formErrors.fullname}>ФИО</InputLabel>
                            <Input id="fullname" name="fullname" value={this.state.user.fullname} onChange={this.handleChange} error={this.state.formErrors.fullname}  minLength="4" maxLength="40"/>
                        </FormControl>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="login">Логин *</InputLabel>
                            <Input id="login" name="login" value={this.state.user.login} onChange={this.handleChange} minLength="3" maxLength="15" required/>
                        </FormControl>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="email">Email</InputLabel>
                            <Input id="email" type="email" name="email" value={this.state.user.email} onChange={this.handleChange} maxLength="40"/>
                        </FormControl>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="password">Пароль *</InputLabel>
                            <Input id="password" name="password" value={this.state.user.password} onChange={this.handleChange} minLength="6" maxLength="20" required/>
                        </FormControl>
                        <Grid item xs={12} className="d-flex justify-end">
                            <Button type="submit" variant="contained" color="primary" className="typeBtn" disabled={!this.state.isValid}>
                                Сохранить
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

