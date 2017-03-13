const React = require('react');
const Validator = require('./validator-component');

var ValidatorContainer = React.createClass({
    getInitialState: function() {
      return {
        errorStates: {
          negativeNumber: false
        },
        isValid: true
      }
    },

    componentWillReceiveProps(newProps) {
      var input = newProps.children.props.value;
      const self = this;
      if (input != null && input != '' && input != undefined) {
        if (parseInt(input) != NaN) {
          if (parseInt(input) < 0) {
            var newState = {
              negativeNumber: true
            };
            self.setState({
              isValid: false,
              errorStates: newState
            });
          }
          this.props.handleValidStateChange(this.props.isValid);
        }
        if (parseInt(input) != NaN) {
          if (parseInt(input) >= 0) {
            var newState = {
              negativeNumber: false
            };
            self.setState({
              isValid: true,
              errorStates: newState
            });
          }
          this.props.handleValidStateChange(this.props.isValid);
        }
      }
    },

    shouldComponentUpdate: function(nextProps, nextState) {
      if((this.state.isValid == nextState.isValid) &&
         (this.state.errorStates.invalidText == nextState.errorStates.invalidText) &&
         (this.state.errorStates.invalidLength == nextState.errorStates.invalidLength) &&
         (this.state.input == nextProps.children.props.value)) {
        return false;
      } else {
        this.props.handleValidStateChange(this.state.isValid);
        return true;
      }
    },

    render: function() {
        return (
            <Validator
              isValid={this.state.isValid}
              childs={this.props.children}
              errorStates={this.state.errorStates}
            />
        );
    }
});

module.exports = ValidatorContainer;
