import React from 'react'
import $ from "jquery";
import Wrapper from "./Wrapper";

const NavCategories = (props) => {
    return (

        <Wrapper>
            {props.category.subcategories == null || props.category.subcategories.length === 0 ?
                <li key={props.category.id} className={"nav-item nav-label"}>
                    <label htmlFor={props.category.id} >
                    <input type={"checkbox"} id={props.category.id} name={props.category.id} style={{marginRight:"5px"}} />
                     {props.category.name}</label>
                </li> :
                <li key={props.category.id}  className="nav-item nav-label ">
                    <label htmlFor={props.category.id} >
                        <input type={"checkbox"} id={props.category.id} name={props.category.id} style={{marginRight:"5px"}}/>
                            {props.category.name}
                            <a className={"text-white "} data-toggle="collapse" href={"#collapseExample-" + props.category.id}
                               role="button" aria-expanded="false" aria-controls={"collapseExample-" + props.category.id}>
                                {/*<span className="nav-label">{props.category.name}</span>*/}
                                <span className="fa arrow" />
                            </a>
                    </label>
                    <ul className=" nav-third-level collapse" id={"collapseExample-" + props.category.id}>
                        {props.category.subcategories.map(c => <NavCategories category={c} key={c.id}/>)}
                    </ul>
                </li>
            }
        </Wrapper>


    );
};
export default NavCategories;