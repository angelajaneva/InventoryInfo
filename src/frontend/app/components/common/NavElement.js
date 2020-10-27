import React from 'react'
import $ from "jquery";

const NavElement = (props) => {

    $(".collapse").collapse("hide");
    return (

        <div>
            {props.category.subcategories == null || props.category.subcategories.length === 0 ?
                <li key={props.category.id} className={"nav-item nav-label p-2"}>
                    <input type={"checkbox"} id={props.category.id} name={props.category.id}/>
                    <label htmlFor={props.category.id}>{props.category.name}</label>
                </li> :
                <li key={props.category.id} className={"nav-item"}>
                    <input type={"checkbox"} id={props.category.id} name={props.category.id}/>
                    <label htmlFor={props.category.id} className={"mx-auto nav-label"}>
                        <span className="nav-label">   {props.category.name}   </span>
                        <button className="btn ui-state-hover" style={{background: "transparent"}} type="button" data-toggle="collapse"
                                data-target={"#collapseExample-"+props.category.id} aria-expanded="false" aria-controls={"#collapseExample-"+props.category.id}>
                            <span className="fa arrow"/>
                        </button>
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