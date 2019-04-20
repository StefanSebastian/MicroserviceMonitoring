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
                <table border="1">
                <thead>
                <tr><th>PID</th><th>Name</th><th>Machine</th></tr>
                </thead>
                <tbody>
                {this.props.onlineServices.map((service) => (
                    <tr key={service.pid}>
                        <td>{service.pid}</td>
                        <td>{service.name}</td>
                        <td>{service.machine}</td>
                    </tr>
                ))}
                </tbody>
                </table>
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