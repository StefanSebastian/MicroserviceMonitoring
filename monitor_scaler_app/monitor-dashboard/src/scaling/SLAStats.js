import React, { Component } from 'react';
import { connect } from 'react-redux';
import { getSLAStats } from '../actions';

class SLAStats extends Component {

    componentDidMount() {
        console.log("starting periodic fetch")
        this.interval = setInterval(() => this.props.dispatch(getSLAStats()), 1200)
    }

    componentWillUnmount() {
        console.log("stopping periodic fetch")
        clearInterval(this.interval);
    }

    render() {
        return(
            <div>
                <h1 className="sla_stats_heading">Requests per second</h1>
                <table border="1">
                <thead>
                <tr><th>Name</th><th>P90RT</th></tr>
                </thead>
                <tbody>
                {this.props.slaStats.map((service) => (
                    <tr key={service.name}>
                        <td>{service.name}</td>
                        <td>{service.reqsPerSec}</td>
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
        slaStats: state.slaStats
    }
}
export default connect(mapStateToProps)(SLAStats);