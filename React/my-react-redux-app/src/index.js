import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import {creaateStore} from 'redux';
import {Provider, connect} from 'react-redux';


const SomeComponent = ({dispatch}) => (
    <button onClick={e => {
        dispatch({type: 'some action'})
    }}> Somebuton </button>
)
const SomeContainer =
connect()(SomeComponent)
    
ReactDOM.render(<SomeContainer/>,
     document.getElementById('root'));

registerServiceWorker();
