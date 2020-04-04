import React, { Component } from 'react';
import OnlineServices from './OnlineServices';
import RequestsPerService from './RequestsPerService';
import AverageRequestTimes from './AverageRequestTimes';

import { Route, Link, BrowserRouter as Router } from 'react-router-dom';

class Monitor extends Component {
    render() {
        return(
            <div>
                <Router>
                    <div>
                        <ul>
                            <li><Link to='/online'>Online services</Link></li>
                            <li><Link to='/nrreq'>Requests per service</Link></li>
                            <li><Link to='/avgtimes'>Average request times</Link></li>
                        </ul>

                        <Route path='/online' component={OnlineServices} />
                        <Route path='/nrreq' component={RequestsPerService} />
                        <Route path='/avgtimes' component={AverageRequestTimes} />
                    </div>
                </Router>
            </div>
        )
    }
}

export default Monitor;