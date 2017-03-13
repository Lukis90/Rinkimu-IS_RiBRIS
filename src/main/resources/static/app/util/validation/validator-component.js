const React = require('react');
const Alert = require('./alert');

const Validator = React.createClass({
  render: function() {
    if (this.props.isValid) {
      return (
        <div>
          {this.props.childs}
        </div>
      )
    } else {
      return (
        <div>
          {this.props.childs}
          <div className="alert-danger text-center" style={{marginTop : '5px'}}>
            <Alert errorStates={this.props.errorStates} />
          </div>
        </div>
      )
    }
  }
});

module.exports = Validator;
