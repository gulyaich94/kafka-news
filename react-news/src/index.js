import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { BrowserRouter as Router } from 'react-router-dom';
import Root from "./Root";


const routing = (
  <Router>
    <Root />
  </Router>
)

ReactDOM.render(routing, document.getElementById('root'))
