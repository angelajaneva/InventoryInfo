import React, {Component} from 'react';
import {Link} from 'react-router';
import service from "../../axios/axios-repository"
import NavCategories from "./NavCategories";
import NavGasses from "./NavGasses";
import ToogleSwitch from "./ToogleSwitch/ToogleSwitch";
import NavYear from "./NavYear";

class Navigation extends Component {

    constructor(props) {
        super(props);

        this.state = {
            categories: [],
            gasses: [],
            years: []
        }
    }

    componentDidMount() {
        const {menu} = this.refs;
        $(menu).metisMenu();
        this.loadCategories();
        this.loadGasses();
        this.loadYears();
    }

    activeRoute(routeName) {
        return this.props.location.pathname.indexOf(routeName) > -1 ? "active" : "";
    }

    secondLevelActive(routeName) {
        return this.props.location.pathname.indexOf(routeName) > -1 ? "nav nav-second-level collapse in" : "nav nav-second-level collapse";
    }

    loadCategories = () => {
        service.getCategories().then((response) => {
            this.setState(prevState => {
                const newValue = {
                    'categories': response.data
                };

                return {
                    ...prevState,
                    ...newValue
                };
                }, [], (err)=>{
                console.log(err)
                alert(err);
            })
        })
    };

    loadGasses = () => {
        service.getGasses().then((response) => {
            this.setState(prevState => {
                const newValue = {
                    'gasses': response.data
                };

                return {
                    ...prevState,
                    ...newValue
                };
            }, [], (err)=>{
                console.log(err)
                alert(err);
            })
        })
    }

    loadYears = () => {
        service.getYears().then((response) => {
            this.setState(prevState => {
                const newValue = {
                    'years': response.data
                };

                return {
                    ...prevState,
                    ...newValue
                };
            }, [], (err)=>{
                console.log(err)
                alert(err);
            })
        })
    }


    render() {
        {
            console.log(this.state.categories)
        }
        return (
            <nav className="navbar-default navbar-static-side" role="navigation">
                <ul className="nav metismenu" id="side-menu" ref="menu">
                    <li className="nav-label" style={{padding:"10%"}}>
                        <ToogleSwitch/>
                        <select value={this.state.years[0]} >
                            {this.state.years.map(y => <NavYear year={y} key={y.id}/>)}
                        </select>
                    </li>
                    <li className={"text-white"}>
                        <Link to="collapseExample" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample">
                            <i className="fa fa-list-ul"/>
                            <span className="nav-label">Гасови</span>
                            <span className="fa arrow"/>
                        </Link>
                        <ul className="nav collapse" id={"collapseExample"}>
                            {this.state.gasses.map(g => <NavGasses gas={g} key={g.id}/>)}
                        </ul>
                    </li>

                    <li className={"text-white"}>
                        <Link to="collapseExample1" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample1">
                            <i className="fa fa-list-ul"/>
                            <span className="nav-label">Категории</span>
                            <span className="fa arrow"/>
                        </Link>
                        <ul className="nav nav-second-level collapse" id={"collapseExample1"}>
                            {this.state.categories.map(c => <NavCategories category={c} key={c.id}/>)}
                        </ul>
                    </li>
                </ul>

            </nav>
        )
    }
}

export default Navigation