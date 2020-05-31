import React, { Component } from 'react';
import { connect } from 'react-redux';
import { scaleUp } from '../actions';
import { scaleDown } from '../actions';

class ScaleActions extends Component {

    constructor(props) {
        super(props);
        this.state = {nameUp: '', nameDown: '', machine: '', pid: ''}

        this.handleScaleUp = this.handleScaleUp.bind(this);
        this.handleScaleDown = this.handleScaleDown.bind(this);
        this.handleNameUpChange = this.handleNameUpChange.bind(this);
        this.handleNameDownChange = this.handleNameDownChange.bind(this);
        this.handlePIDChange = this.handlePIDChange.bind(this);
        this.handleMachineChange = this.handleMachineChange.bind(this);
    }


    handleScaleUp(event) {
        console.log(this.state.nameUp);
        this.props.dispatch(scaleUp(this.state.nameUp));
        event.preventDefault();
    }

    handleScaleDown(event) {
        console.log(this.state.nameDown);
        console.log(this.state.pid);
        console.log(this.state.machine);
        this.props.dispatch(scaleDown(this.state.nameDown, this.state.machine, this.state.pid));
        event.preventDefault();
    }

    handleNameUpChange(event) {
        this.setState({nameUp: event.target.value});
    }

    handleNameDownChange(event) {
        this.setState({nameDown: event.target.value});
    }

    handlePIDChange(event) {
        this.setState({pid: event.target.value});
    }

    handleMachineChange(event) {
        this.setState({machine: event.target.value});
    }

    render() {
        let microservices = this.props.onlineServices;
        let nameSet = new Set()
        let machineSet = new Set()
        microservices.forEach(m => {
            nameSet.add(m.name); 
            machineSet.add(m.machine)
        })

        let nameOpt = []
        nameSet.forEach(name => 
            nameOpt.push(<option key={name} value={name}>{name}</option>))
        let machineOpt = []
        machineSet.forEach(name => 
            machineOpt.push(<option key={name} value={name}>{name}</option>))
        


        return(
            <div>
                <h1 className="sla_stats_heading">Scale Actions</h1>

                <form onSubmit={this.handleScaleUp}>
                    <label>
                        <select value={this.state.nameUp} onChange={this.handleNameUpChange}>
                            <option>Select service</option>
                            {nameOpt}
                        </select>
                     
                    </label>
                    <br />
                    <input type="submit" value="Scale Up" />
                </form>
                <br />

                <form onSubmit={this.handleScaleDown}>
                    <label>
                        <select value={this.state.nameDown} 
                            onChange={this.handleNameDownChange}>
                            <option>Select service</option>
                            {nameOpt}
                        </select>
                    </label>
                    <br />
                    <label>
                        <select value={this.state.machine} 
                            onChange={this.handleMachineChange}>
                            <option>Select host</option>
                            {machineOpt}
                        </select>
                    </label>
                    <br />
                    <label>
                        PID:
                        <input type="text" value={this.state.pid} 
                            onChange={this.handlePIDChange} />
                    </label>
                    <br />
                    <input type="submit" value="Scale Down" />
                </form>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        onlineServices: state.onlineServices
    }
}

export default connect(mapStateToProps)(ScaleActions);