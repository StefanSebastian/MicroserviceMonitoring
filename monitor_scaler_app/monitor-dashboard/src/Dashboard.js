import React, { Component } from 'react';
import OnlineServices from './OnlineServices';
import RequestsPerService from './RequestsPerService';
import AverageRequestTimes from './AverageRequestTimes';
import SLAStats from './SLAStats';

class Dashboard extends Component {
    render() {
        return(
            <div>
                <OnlineServices />
                <SLAStats />
                <RequestsPerService />
                <AverageRequestTimes />
            </div>
        )
    }
}

export default Dashboard;