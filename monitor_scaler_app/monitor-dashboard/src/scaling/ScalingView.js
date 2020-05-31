import React, { Component } from 'react';
import SLAStats from './SLAStats';
import ScaleActions from './ScaleActions';

class ScalingView extends Component {
    render() {
        return(
            <div style={wrapperSyle}>
                <div style={leftStyle}>
                    <SLAStats />
                </div>
                <div style={rightStyle}>
                    <ScaleActions />
                </div>
            </div>
        )
    }
}

const wrapperSyle = {
    display: "flex"
}
const leftStyle = {
    flex: "0 0 50%"
}
const rightStyle = {
    fels: "1"
}

export default ScalingView;