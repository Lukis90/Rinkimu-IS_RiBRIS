const React = require('react');
const axios = require('axios');
const DistrictListComponent = require('../../presentations/lists/districts-list-presentation');

var DistrictListContainer = React.createClass({
  getInitialState: function() {
    return {
      districts: [],
      searchText: ''
    };
  },

  componentWillMount: function() {
    var self = this;
    if (self.props.params.constituencyId == undefined) {
      axios.get('http://localhost:8090/polling-districts/')
      .then(function (response) {
        self.setState({
            districts: response.data,
        });
      });
    } else {
      axios.get('http://localhost:8090/constituencies/' + self.props.params.constituencyId)
      .then(function (response) {
        self.setState({
            districts: response.data.pollingDistricts,
        });
      });
    }
  },

  handleSearchTextChange: function(e) {
    var text = e.target.value;
    this.setState({
      searchText: text
    });
  },

  handleDistrictEdit: function(district) {
    var self = this;
    return function() {
      self.context.router.push('/admin/districts/edit/' + district.id);
    }
  },

  handleDistrictRemove: function(district) {
    var self = this;
    return function() {
      axios.delete('http://localhost:8090/polling-districts/'+ district.id).then(function() {
        axios.get('http://localhost:8090/polling-districts/')
        .then(function (response) {
          self.setState({
            districts: response.data
          });
        });
      });
    };
  },

  render: function() {
    return (
      <DistrictListComponent
        districts={this.state.districts}
        searchText={this.state.searchText}
        onSearchTextChange={this.handleSearchTextChange}
        onAddClick={this.handleAddDistrict}
        onEditItem={this.handleDistrictEdit}
        onRemoveItem={this.handleDistrictRemove}
      />
    );
  }
});

DistrictListContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = DistrictListContainer;
