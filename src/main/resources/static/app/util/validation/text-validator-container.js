const React = require('react');
const Validator = require('./validator-component');

var ValidatorContainer = React.createClass({
    getInitialState: function() {
      return {
        errorStates: {
          invalidText: false,
          invalidLength: false
        },
        isValid: true
      }
    },

    componentWillReceiveProps(newProps) {
      const exp = new RegExp('^[a-p,r-v,yząčęėįšųūž]*$', 'i');
      var input = newProps.children.props.value;
      const self = this;
      var newState = this.state.errorStates;
      if (input != null && input != '' && input != undefined) {
        if (!exp.test(input)) {
          var newState = {
            invalidText: true,
            invalidLength: newState.invalidLength
          };
          self.setState({
            isValid: false,
            errorStates: newState
          });
        }
        if (exp.test(input)) {
          var newState = {
            invalidText: false,
            invalidLength: newState.invalidLength
          };
          self.setState({
            isValid: true,
            errorStates: newState
          });
        }
        if (input.length < 4) {
          var newState = {
            invalidText: newState.invalidText,
            invalidLength: true
          };
          self.setState({
            isValid: false,
            errorStates: newState
          });
        }
        if (input.length >= 4) {
          var newState = {
            invalidText: newState.invalidText,
            invalidLength: false
          };
          self.setState({
            isValid: true,
            errorStates: newState
          });
        }
        if (input.length >= 4 && !exp.test(input)) {
          var newState = {
            invalidText: true,
            invalidLength: newState.invalidLength
          };
          self.setState({
            isValid: false,
            errorStates: newState
          });
        }
        if (input.length >= 4 && exp.test(input)) {
          var newState = {
            invalidText: false,
            invalidLength: newState.invalidLength
          };
          self.setState({
            isValid: true,
            errorStates: newState
          });
        }
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
