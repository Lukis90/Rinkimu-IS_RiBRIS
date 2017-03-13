const React = require('react');

const alert = React.createClass({
  getInitialState: function() {
    return {
      text: '',
      style: ''
    }
  },

  componentDidMount: function() {
    this.setState({
      style: this.props.style
    });
  },

  componentWillReceiveProps: function(newProps) {
    this.setState({
      text: newProps.text
    });
  },

  handleCloseClick: function() {
    this.setState({
      text: ''
    });
  },

  render: function() {
    if (this.state.text == null || this.state.text == '') {
      return null;
    } else {
      return (
        <div className={this.props.style}>
          <button className="close" data-dismiss="alert" aria-label="close" onClick={this.handleCloseClick}>×</button>
          {this.state.text}
        </div>
      );
    }
  }
});

module.exports = alert;
