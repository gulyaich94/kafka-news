import React from 'react';
import axios from 'axios';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Title from './Title';
import MyContext from './MyContext.js';


export class MainPage extends React.Component {
    state = {
        user: {
            principal: {}
        }
    }

    componentDidMount() {
        axios.get(`http://localhost:8082/api/auth/user`, { headers: { "Authorization": `${this.context.token.tokenType} ${this.context.token.accessToken}` } })
            .then(res => {
                const user = res.data;
                this.setState({ user });
            })
    }

    render() {

        return (
            <>
                <Paper className="typePaper">
                    <Title>Добро пожаловать!</Title>
                </Paper>
                <Paper className="typePaper">
                    <Title>{this.state.user && this.state.user.principal.fullname}</Title>
                    <Typography>
                        login: {this.state.user && this.state.user.principal.login}
                    </Typography>
                </Paper>
            </>
        );
    }
}

MainPage.contextType = MyContext;
