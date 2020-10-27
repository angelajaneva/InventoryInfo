import React from 'react'
import $ from "jquery";
import Wrapper from "./Wrapper";

const NavElement = (props) => {

    $(".collapse").collapse("hide");
    return (

        <Wrapper>
            {props.category.subcategories == null || props.category.subcategories.length === 0 ?
                <li key={props.category.id} className={"nav-item nav-label p-2"}>
                    <input type={"checkbox"} id={props.category.id} name={props.category.id}/>
                    <label htmlFor={props.category.id}>{props.category.name}</label>
                </li> :
                <li key={props.category.id} className="nav-item nav-label p-2">
                    <input type={"checkbox"} id={props.category.id} name={props.category.id}/>
                    <label htmlFor={props.category.id} >
                        {props.category.name}
                        <a data-toggle="collapse" href={"#collapseExample-" + props.category.id}
                           role="button" aria-expanded="false" aria-controls={"collapseExample-" + props.category.id}>
                            {/*<span className="nav-label">{props.category.name}</span>*/}
                            <span className="fa arrow"/>
                        </a>
                    </label>
                    <ul className=" nav-third-level collapse" id={"collapseExample-" + props.category.id}>
                        {props.category.subcategories.map(c => <NavElement category={c} level={props.level + 1}
                                                                           key={c.id}/>)}
                    </ul>
                </li>
            }
        </Wrapper>


    );
};
export default NavElement;