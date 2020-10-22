import React from 'react'
import {Link} from "react-router";

const NavElement = (props) => {

    const getCategories = () => {
        return props.cats.map(category => {
            return (
                <div key={category.id}>
                    <li>
                        <Link to="/minor">{category.name}</Link>
                    </li>
                </div>
            )
        })
    }


    return (
        {getCategories}
    );
};
export default NavElement;