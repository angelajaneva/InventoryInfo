import React from 'react'
import Wrapper from "./Wrapper";
import {Link} from "react-router";



const NavCategory = (props) => {
    return (
        <Wrapper>
            {
                props.category.subcategories == null || props.category.subcategories.length === 0 ?
                    <li key={props.category.id}>
                        <input type={"checkbox"} id={props.category.id}
                               name={props.category.id}/>
                        <a>{props.category.name}</a>
                    </li>
                    :
                    <li key={props.category.id}>
                        <input type={"checkbox"} id={props.category.id}
                               name={props.category.id}/>
                        <a data-toggle="collapse" href={"#collapseExample-" + props.category.id}
                               aria-expanded="false" aria-controls={"collapseExample-" + props.category.id}>
                            {props.category.name}
                            <span className="fa arrow"/>
                        </a>
                        <ul className="nav nav-third-level collapse" id={"collapseExample-" + props.category.id}>
                            {props.category.subcategories.map(c => <NavCategory category={c} key={c.id}/>)}
                        </ul>
                    </li>
            }
        </Wrapper>
    );
};
export default NavCategory;