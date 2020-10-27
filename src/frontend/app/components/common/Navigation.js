
import React, {Component} from 'react';
import {Link, Location} from 'react-router';
import ToogleSwitch from "./ToogleSwitch/ToogleSwitch"
import service from "../../axios/axios-repository"
import NavElement from "./NavElement";

class Navigation extends Component {

    constructor(props) {
        super(props);

        this.state = {
            categories: []
        }
    }

    componentDidMount() {
        const {menu} = this.refs;
        $(menu).metisMenu();
        this.loadCategories();
    }

    activeRoute(routeName) {
        return this.props.location.pathname.indexOf(routeName) > -1 ? "active" : "";
    }

    secondLevelActive(routeName) {
        return this.props.location.pathname.indexOf(routeName) > -1 ? "nav nav-second-level collapse in" : "nav nav-second-level collapse";
    }

    loadCategories = () => {
        service.getCategories().then((response) => {
            this.setState({
                categories: response.data
            })
        })
    };


    render() {
        {
            console.log(this.state.categories)
        }
        return (
            <nav className="navbar-default navbar-static-side" role="navigation">
                <ul className="nav metismenu text-white" id="side-menu" ref="menu">
                    <li className="nav-header">
                        {/*<ToogleSwitch/>*/}
                    </li>
                    <li >
                        <Link to="#collapseExample" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample">
                            <i className="fa fa-list-ul"/>
                            <span className="nav-label">Години</span>
                            <span className="fa arrow"/>
                        </Link>
                        <ul className="nav collapse" id={"collapseExample"}>
                        </ul>
                    </li>

                    <li >
                        <Link to="#collapseExample1" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample1">
                            <i className="fa fa-list-ul"/>
                            <span className="nav-label">Категории</span>
                            <span className="fa arrow"/>
                        </Link>
                        <ul className="nav-second-level collapse" id={"collapseExample1"}>
                            {this.state.categories.map(c => <NavElement category={c} level={3} key={c.id}/>)}
                        </ul>
                    </li>
                </ul>

            </nav>
        )
    }
}

export default Navigation