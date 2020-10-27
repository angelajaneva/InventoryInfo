import React from 'react'
import $ from "jquery";

const NavElement = (props) => {

    $(".collapse").collapse("hide");
    return (

        <div>
            {props.category.subcategories == null || props.category.subcategories.length === 0 ?
                <li key={props.category.id} className={"nav-item"}>
                    <input type={"checkbox"} id={props.category.id} name={props.category.id}/>

                    <label htmlFor={props.category.id}>{props.category.name}</label>
                </li> :
                <li key={props.category.id} className={"nav-item"}>
                    <input type={"checkbox"} id={props.category.id} name={props.category.id}/>
                    <label htmlFor={props.category.id} className={"mx-auto"}>
                        <a className={"mx-auto"} data-toggle="collapse" href={"#collapseExample-"+props.category.id}
                           role="button" aria-expanded="false" aria-controls={"collapseExample-"+props.category.id}>
                            <span className="nav-label">{props.category.name}</span>
                            <span className="fa arrow"/></a>
                    </label>
                    <div>
                    <ul className={"collapse"} id={"collapseExample-"+props.category.id}>
                        {props.category.subcategories.map(c => <NavElement category={c} level={props.level + 1}
                                                                           key={c.id}/>)}
                    </ul>
                    </div>
                </li>
            }
        </div>


    );
};
export default NavElement;