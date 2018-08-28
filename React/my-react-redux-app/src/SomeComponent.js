import React from 'react';
import ReactDOM from 'react-dom';

const SomeComponent = ({dispatch}) =>(
    <button onClick={e=>{
        dispatch({type: 'some action'})
    }}> Domebuton </button>
)
const SomeContainer =
    connect()(SomeCompoent)