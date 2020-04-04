import React, { Component } from 'react';
import { connect } from 'react-redux';
import { getRequestsPerService } from './actions';
import { Bar } from 'react-chartjs-2';

class RequestsPerService extends Component {
    
    componentDidMount() {
        console.log("starting periodic fetch")
        this.interval = setInterval(() => this.props.dispatch(getRequestsPerService()), 1000)
    }

    componentWillUnmount() {
        console.log("stopping periodic fetch")
        clearInterval(this.interval)
    }

    render() {
        console.log(this.props.requestsPerService);
        var labels = []
        var values = []
        var req
        for (req of this.props.requestsPerService) {
            labels.push(req.serviceName)
            values.push(req.nrRequests)
        }

        var data = {
            labels: labels,
            label: "Requests per service in the last minute",
            datasets: [{ data: values }]
        }
        var options = {
            maintainAspectRatio: false
        }
        return(
            <div>
                <h1>Nr requests per service in the last minute</h1>
                <Bar data={data} options={options} height={300} width={400}/>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        requestsPerService: state.requestsPerService
    }
}

export default connect(mapStateToProps)(RequestsPerService);