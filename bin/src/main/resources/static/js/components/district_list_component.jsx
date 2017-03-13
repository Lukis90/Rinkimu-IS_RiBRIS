var DistrictListComponent = React.createClass({
    render: function() {
        var self = this;
        var districtsList = this.props.districts.map(function(district, index) {
            return (
                <tr key={index}>
                    <td>{district.id}</td>
                    <td>{district.name}</td>
                    <td>{district.address}</td>
                    <td>{district.numOfVoters}</td>
                    <td>{district.representative}</td>

                    <td><button className="btn btn-success btn-sm" onClick={self.props.onEditItem(district)}>
                        <i className="fa fa-pencil" aria-hidden="true"></i>
                    </button>
                     <button className="btn btn-danger btn-sm" onClick={self.props.onRemoveItem(district)}>
                        <i className="fa fa-times" aria-hidden="true"></i>
                    </button></td>
                </tr>
            );
        });

        return (
            <div className="container"><br />
                <button className="btn btn-success" onClick={this.props.onAddClick}>
                    <i className="fa fa-plus" aria-hidden="true"></i> Registruoti naują apylinkę
                </button>
                <br /><br />
                <div className="panel panel-default">
                <div className="panel-heading"><strong>Apylinkių sąrašas</strong></div>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Pavadinimas</th>
                                <th>Adresas</th>
                                <th>Balsuotojų skaičius</th>
                                <th>Apylinkės atstovas</th>
                                <th>Redaguoti</th>
                            </tr>
                        </thead>
                        <tbody>
                            {districtsList}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
});

DistrictListComponent.propTypes = {
    districts: React.PropTypes.array.isRequired,
    onAddClick: React.PropTypes.func.isRequired
}

window.DistrictListComponent = DistrictListComponent;