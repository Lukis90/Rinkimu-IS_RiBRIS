const React = require('react');
const axios = require('axios');
const Link = require('react-router').Link;
const SingleMandateListPresentation = require('../../presentations/lists/single-mandate-results-presentation');

var SingleMandateListContainer = React.createClass({
  getInitialState: function() {
    return {
      constituencies: []
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/candidates-results/single-mandate/progress/')
    .then(function (response) {
      self.setState({
        constituencies: response.data
      });
    });
  },

  cellButton(cell, row, enumObject, rowIndex) {
    var link = "/single-mandate-districts/" + this.state.constituencies[rowIndex].constituency.id;
    return (
       <Link 
          to={link} 
       >
       {this.state.constituencies[rowIndex].constituency.name}
       </Link>
    )
  },

  render: function() {
    return (
      <SingleMandateListPresentation
        constituencies={this.state.constituencies}
        activeFormatter={this.props.activeFormatter}
        onClickProductSelected={this.handleClickProductSelected}
        cellButton={this.cellButton}
      />
    );
  }
});

SingleMandateListContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

module.exports = SingleMandateListContainer;