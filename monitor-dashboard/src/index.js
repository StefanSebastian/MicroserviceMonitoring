import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';


import { createStore, applyMiddleware, combineReducers } from 'redux';
import thunkMiddleware from 'redux-thunk';
import { Provider } from 'react-redux';

//const store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

ReactDOM.render(
<App />, 
document.getElementById('root')
);
