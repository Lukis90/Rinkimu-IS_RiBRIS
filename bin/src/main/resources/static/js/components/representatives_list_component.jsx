var RepresentativesListComponent = React.createClass({
    render: function() {
        var self = this;
        var representativesList = this.props.representatives.map(function(representative, index) {
            return (
                <tr key={index}>
                    <td>{representative.id}</td>
                    <td>{representative.name}</td>
                    <td>{representative.surname}</td>
                    <td>{representative.district}</td>
                   

                    <td><button className="btn btn-success btn-sm" onClick={self.props.onEditItem(representative)}>
                        <i className="fa fa-pencil" aria-hidden="true"></i>
                    </button>
                     <button className="btn btn-danger btn-sm" onClick={self.props.onRemoveItem(representative)}>
                        <i className="fa fa-times" aria-hidden="true"></i>
                    </button></td>
                </tr>
            );
        });

        return (
            <div className="container"><br />
                <button className="btn btn-success" onClick={this.props.onAddClick}>
                    <i className="fa fa-plus" aria-hidden="true"></i> Registruoti naują atstovą
                </button>
                <br /><br />
                <div className="panel panel-default">
                <div className="panel-heading"><strong>Atstovų sąrašas</strong></div>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Vardas</th>
                                <th>Pavardė</th>
                                <th>Atstovaujama apylinkė</th>
                                <th>Redaguoti</th>
                            </tr>
                        </thead>
                        <tbody>
                            {representativesList}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
});

RepresentativesListComponent.propTypes = {
    representatives: React.PropTypes.array.isRequired,
    onAddClick: React.PropTypes.func.isRequired
}

window.RepresentativesListComponent = RepresentativesListComponent;