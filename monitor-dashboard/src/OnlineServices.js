import React, { Component } from 'react';
import { connect } from 'react-redux';
import { getOnlineServices } from './actions';

class OnlineServices extends Component {

    componentDidMount() {
        console.log("starting periodic fetch");
        this.interval = setInterval(() => this.props.dispatch(getOnlineServices()), 1000);
    }

    componentWillUnmount() {
        console.log("stopping periodic fetch");
        clearInterval(this.interval);
    }

    render() {
        return(
            <div>
                <h1 className="online_services_heading">Online services</h1>
                {this.props.onlineServices.map((service) => (
                    <div key={service.pid}>
                        <div>{service.pid}</div>
                        <div>{service.name}</div>
                        <div>{service.machine}</div>
                    </div>
                ))}
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        onlineServices: state.onlineServices
    }
}

export default connect(mapStateToProps)(OnlineServices);