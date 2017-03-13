const React = require('react');

const Alert = React.createClass({
  render: function() {
    var errorStates = this.props.errorStates;
    if (errorStates.invalidText) {
      return <div>Galite naudoti tik lietuviškas raides</div>
    }
    if (errorStates.invalidLength) {
      return <div>Įveskite bent 4 simbolius</div>
    }
    if (errorStates.negativeNumber) {
      return <div>Galite įvesti tik teigiamas vertes</div>
    }
  }
});

module.exports = Alert;
