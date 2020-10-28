import React from 'react'
import $ from "jquery";


const NavYear = (props) => {

    return (
        <option id={props.year.id} value={props.year.id} className={"text-dark"}>
            {props.year.year}
        </option>
    )
}

export default NavYear