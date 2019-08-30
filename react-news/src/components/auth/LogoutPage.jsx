import React from 'react';
import Swal from 'sweetalert2'
import MyContext from '../MyContext.js';

export class LogoutPage extends React.Component {
    
    componentDidMount() {
        const newtoken = {
            accessToken: '',
            tokenType: ''
        };
        this.context.updateToken(newtoken);
        
        Swal.fire(
            'Успешно!',
            'До новых встреч',
            'success'
        );

        this.props.history.push(`/`);
    }

    render() {
        return(
            <>
            </>
        );
    }
}

LogoutPage.contextType = MyContext;