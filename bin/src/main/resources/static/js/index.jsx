var App = React.createClass({
    render: function() {
        return (
            <div style={{ paddingTop: '20px' }}>
                <NavBar/>   
                {this.props.children}
            </div>
        );
    }
});

var DistrictRowContainer = React.createClass({
    render: function() {
        return (
            <h4>Greitai čia bus apylinkių sąrašas priskirtas konkrečiai apygardai</h4>
        );
    }
});



var NavBar = React.createClass({
    render: function() {
        return (
            <ul className="nav nav-tabs">
                <li role="presentation">
                    <IndexLink to="/" activeClassName="active"><i className="fa fa-home fa-2x" aria-hidden="true"></i></IndexLink>
                </li>
                <li role="presentation">
                    <Link to="/apygardos" activeClassName="active">Apygardos</Link>
                </li>
                 <li role="presentation">
                    <Link to="/apylinkes" activeClassName="active">Apylinkės</Link>
                </li>
                <li role="presentation">
                    <Link to="/atstovai" activeClassName="active">Atstovai</Link>
                </li>
                <li role="presentation">
                    <Link to="/partijos" activeClassName="active">Partijos</Link>
                </li>
                <li role="presentation">
                    <Link to="/kandidatai" activeClassName="active">Kandidatai</Link>
                </li>
                <li role="presentation">
                    <Link to="/atsijungti" activeClassName="active" style={{marginLeft: '385px'}}>
                        <i className="fa fa-sign-out" aria-hidden="true"></i>
                        (Atsijungti)
                    </Link>
                </li>
            </ul>
        );
    }
});



var Main = React.createClass({
    render: function() {
        return (
            <div className="container">
                <h3>Labas, Administratoriau</h3><br />
                <h4>Pagrindinis puslapis</h4>
                <p>Čia bus puslapio statistika ect..</p>
            </div>
        );
    }
});

var NoMatch = React.createClass({
  render: function() {
    return <div>Route did not match</div>;
  }
});


var Router = ReactRouter.Router;
var Route = ReactRouter.Route;
var IndexRoute = ReactRouter.IndexRoute;
var hashHistory = ReactRouter.hashHistory;
var Link = ReactRouter.Link;
var IndexLink = ReactRouter.IndexLink;


ReactDOM.render((
  <Router history={hashHistory}>
    <Route path="/" component={App}>
      <IndexRoute component={Main} />
      <Route path="/apygardos" component={ConstituenciesListContainer} />
        <Route path="/apygardos/prideti" component={AddConstituencyContainer} />
        <Route path="/apygardos/redaguoti/:constituencyId" component={EditConstituencyContainer} />
        <Route path="/apygardos/trinti/:constituencyId" component={ConstituenciesListContainer} />
     <Route path="/apylinkes" component={DistrictListContainer} />
        <Route path="/apygarda/:constituencyId/apylinkes" component={DistrictRowContainer} />
        <Route path="/apylinkes/prideti" component={AddDistrictContainer} />
        <Route path="/apylinkes/redaguoti/:districtId" component={EditDistrictContainer} />
        <Route path="/apylinkes/trinti/:districtId" component={ConstituenciesListContainer} />
     <Route path="/atstovai" component={RepresentativesListContainer} />
        <Route path="/atstovai/prideti" component={AddRepresentativeContainer} />
        <Route path="/atstovai/redaguoti/:representativeId" component={EditRepresentativeContainer} />
        <Route path="/atstovai/trinti/:representativeId" component={RepresentativesListContainer} />
      <Route path="*" component={NoMatch}/>
    </Route>
  </Router>
), document.getElementById('root'));