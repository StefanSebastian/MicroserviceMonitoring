import React, { Component } from 'react';
import OnlineServices from './OnlineServices';
import RequestsPerService from './RequestsPerService';
import AverageRequestTimes from './AverageRequestTimes';
import ScalingView from './scaling/ScalingView';


class Dashboard extends Component {

    render() {
        return(
            <section style={sectionStyle}>
                <div><OnlineServices /></div>
                <div><RequestsPerService /></div>
                <div><ScalingView /></div>
                <div><AverageRequestTimes /></div>    
            </section>
        )
    }
}

const sectionStyle = {
    display: "grid",
    gridTemplateColumns: '1fr 1fr',
    gridTemplateRows: 'auto auto',
    gridGap: "10px",
    gridAutoFlow: "column",
}

export default Dashboard;