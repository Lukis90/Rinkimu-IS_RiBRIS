const React = require('react');
const NavLink = require('../navigation/nav-link');
const Link = require('react-router').Link;
const ReactBsTable = require("react-bootstrap-table");
const BootstrapTable = ReactBsTable.BootstrapTable;
const TableHeaderColumn = ReactBsTable.TableHeaderColumn;

const SingleMandateComponent = React.createClass({
  render: function() {
    var self = this;
    var ConstituenciesList = [];

    self.props.constituencies.map(function(constituency, index) {
	    ConstituenciesList.push(
    		{
				id: index+1,
				constituencyId: constituency.constituency.id,
				name: constituency.constituency.name,
				districtsCount: constituency.totalNumOfDistricts,
				votedDistrictsCount: constituency.districtsWithResults
			}
			);
	});

	

    return (
      <div>
      <h4>Balsavimo rezultatai vienmandatėse apygardose</h4>
		<BootstrapTable height='auto' data={ConstituenciesList} striped={true} pagination search searchPlaceholder='ieškoti'>
			<TableHeaderColumn row='0' colSpan='2' headerAlign='center' dataAlign='center'>Apygardos</TableHeaderColumn>
        	<TableHeaderColumn row='1'  width='35px' dataField='id' isKey>#</TableHeaderColumn>
        	<TableHeaderColumn row='1' headerAlign='center'  dataFormat={self.props.cellButton.bind(this)} dataField='name' >Pavadinimas</TableHeaderColumn>
        	<TableHeaderColumn row='0' colSpan='2' headerAlign='center'>Apylinkių skaičius</TableHeaderColumn>
       	 	<TableHeaderColumn row='1' width='160' headerAlign='center' dataAlign='center' dataField='districtsCount'>iš viso</TableHeaderColumn>
        	<TableHeaderColumn row='1' width='160' headerAlign='center' dataAlign='center' dataField='votedDistrictsCount'>duomenis atsiuntė</TableHeaderColumn>
		</BootstrapTable>
		</div>
		
    )
  }
});

module.exports = SingleMandateComponent;