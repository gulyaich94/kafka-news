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
import MyContext from '../MyContext.js';


export class NewsCreationPage extends React.Component {
    state = {
        isValid: false,
        news: {
            title: '',
            body: '',
        },
        formErrors: {
            title: false,
            body: false
        },
    }

    handleChange = (event) => {
        const fieldName = event.target.name;
        const filedValue = event.target.value;
        this.setState(prevState => ({
            news: {                   
                ...prevState.news,    
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
        const { title, body } = this.state.news;
        const requiredFields = { [title]: title, [body]: body };

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
        const news = this.state.news;
        news['date'] = new Date();
        axios.post('http://localhost:8082/kafka/publish/', 
            news
        )
            .then((response) => {
                console.log(response);
                this.setState(function (state, props) {
                    return {
                        news: {
                            title: '',
                            body: '',
                        },
                        formErrors: {
                            title: false,
                            body: false
                        },
                        isValid: false,
                    }
                });
            })
            .catch((error) => {
                console.log(error);
                
            });
    }

    render() {
        return (
            <>
                <Paper className="typePaper">
                    <Title>Добавить новость</Title>
                    <form className={"d-flex f-wrap typeForm"} autoComplete="off" onSubmit={this.handleSubmit}>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="title" error={this.state.formErrors.title}>Заголовок *</InputLabel>
                            <Input id="title" name="title" value={this.state.news.title} onChange={this.handleChange} error={this.state.formErrors.title} required />
                        </FormControl>
                        <FormControl className={"typeM MuiFormControl-fullWidth"}>
                            <InputLabel htmlFor="body">Текст *</InputLabel>
                            <Input id="body" name="body" value={this.state.news.body} onChange={this.handleChange} maxLength="1024" multiline required/>
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

NewsCreationPage.contextType = MyContext;
