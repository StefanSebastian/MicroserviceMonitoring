import React, { Component } from 'react';
import { connect } from 'react-redux';
import { getAverageRequestTimesPerService } from './actions';
import { Bar } from 'react-chartjs-2';

class AverageRequestTimes extends Component {
    
    componentDidMount() {
        console.log("starting periodic fetch")
        this.interval = setInterval(() => this.props.dispatch(getAverageRequestTimesPerService()), 1000)
    }

    componentWillUnmount() {
        console.log("stopping periodic fetch")
        clearInterval(this.interval)
    }

    render() {
        console.log(this.props.averageRequestTimes);
        var labels = []
        var values = []
        var req
        for (req of this.props.averageRequestTimes) {
            labels.push(req.service)
            values.push(req.duration)
        }

        var data = {
            labels: labels,
            label: "Average request times per service in the last minute",
            datasets: [{ data: values }]
        }
        var options = {
            maintainAspectRatio: false
        }
        return(
            <div>
                <h1>Average request times per service in the last minute</h1>
                <Bar data={data} options={options} height={300} width={400}/>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        averageRequestTimes: state.averageRequestTimes
    }
}

export default connect(mapStateToProps)(AverageRequestTimes);