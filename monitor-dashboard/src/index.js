import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import monitorReducer from './monitorReducer';

import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import { Provider } from 'react-redux';

const store = createStore(monitorReducer, applyMiddleware(thunkMiddleware));

ReactDOM.render(
<Provider store={store}>
<App />
</Provider>, 
document.getElementById('root')
);
