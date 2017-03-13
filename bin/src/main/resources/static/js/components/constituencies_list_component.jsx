var ConstituenciesListComponent = React.createClass({
    render: function() {
        var self = this;
        var constituenciesList = this.props.constituencies.map(function(constituency, index) {
            return (
                <tr key={index}>
                    <td>{constituency.id}</td>
                    <td>{constituency.name}</td>
                    <td>
                        <button className="btn btn-info btn-sm" onClick={self.props.onDistrictsList(constituency)}>
                            <i className="fa fa-list" aria-hidden="true"></i>
                            &nbsp; Priskirtos apylinkės
                        </button>
                    </td>
                        <td><button className="btn btn-success btn-sm" onClick={self.props.onEditItem(constituency)}>
                            <i className="fa fa-pencil" aria-hidden="true"></i>
                        </button>
                        <button className="btn btn-danger btn-sm" onClick={self.props.onRemoveItem(constituency)}>
                            <i className="fa fa-times" aria-hidden="true"></i>
                        </button></td>
                </tr>
            );
        });
        
        return (
            <div className="container"><br />
                <button className="btn btn-success" onClick={this.props.onAddClick}>
                    <i className="fa fa-plus" aria-hidden="true"></i> Registruoti naują apygardą
                </button>
                <br /><br />
                <div className="panel panel-default">
                <div className="panel-heading"><strong>Apygardų sąrašas</strong></div>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Pavadinimas</th>
                                <th>Apylinkių sąrašas</th>
                                <th>Redaguoti</th>
                            </tr>
                        </thead>
                        <tbody>
                            {constituenciesList}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
});

ConstituenciesListComponent.propTypes = {
        constituencies: React.PropTypes.array.isRequired,
        onAddClick: React.PropTypes.func.isRequired,
        onRemoveItem: React.PropTypes.func.isRequired,
};

window.ConstituenciesListComponent = ConstituenciesListComponent;