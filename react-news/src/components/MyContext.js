import React from 'react';

const MyContext = React.createContext({
    token: {
        accessToken: '',
        tokenType: ''
      },
    updateToken: () => {},
});

export default MyContext;