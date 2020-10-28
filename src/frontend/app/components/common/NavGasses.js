import React from 'react'
import $ from "jquery";
import Wrapper from "./Wrapper";

const NavGasses = (props) => {

    return (
        <li key={props.gas.id} style={{paddingLeft: "5%"}} className={"nav-item nav-label p-2"}>
            <input type={"checkbox"} id={props.gas.id} name={props.gas.id}/>
            <label htmlFor={props.gas.id}>{props.gas.name}</label>
        </li>
    );
};
export default NavGasses;