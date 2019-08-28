import React from 'react';
import { Header } from './components/common/Header';
import { Main } from './components/Main';
import MyContext from './components/MyContext.js';
import { withCookies, Cookies } from 'react-cookie';
import { instanceOf } from 'prop-types';

var styles = {
  display: 'flex'
};

export class App extends React.Component {
  
  static propTypes = {
    cookies: instanceOf(Cookies).isRequired
  };

  constructor(props) {
    super(props);
    const { cookies } = props;

    let token = {};
    if (cookies.get('token')) {
      token = cookies.get('token');
      console.log("exist");
    } else {
      token = {
        accessToken: '',
        tokenType: ''
      };
      console.log("not exist");
    }

    this.state = {
      token: token,
      updateToken: (token) => {
        const { cookies } = this.props;
        this.setState({ token });
        cookies.set('token', token, { path: '/' });
      }
    };
  }

  render() {
    return (
        <MyContext.Provider value={this.state}>
          {console.log(this.state.token)}
          <div style={styles}>
            <Header />
            <Main />
          </div>
        </MyContext.Provider>
    );
  }
}
export default withCookies(App);
