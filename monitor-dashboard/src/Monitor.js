import React, { Component } from 'react';
import OnlineServices from './OnlineServices';
import RequestsPerService from './RequestsPerService';

class Monitor extends Component {
    render() {
        return(
            <div>
                <OnlineServices />
                <RequestsPerService />
            </div>
        )
    }
}

export default Monitor;