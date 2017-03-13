const React = require('react');
const ReactRouter = require('react-router');
const Router = ReactRouter.Router;
const Route = ReactRouter.Route;
const IndexRoute = ReactRouter.IndexRoute;
const hashHistory = ReactRouter.hashHistory;
const NavAdmin = require('../containers/navigation/navbar-admin-container');
const NavRepresentative = require('../containers/navigation/navbar-representative-container');
const NavPublic = require('../presentations/navigation/navbar-public-presentation');
const Login = require('../presentations/navigation/login-presentation');

const AddConstituency = require('../containers/add-constituency-container');
const AddDistrict = require('../containers/add-district-container');
const AddParty = require('../containers/add-party-container');
const AddRepresentative = require('../containers/add-representative-container');
const AddCandidate = require('../containers/add-candidate-container');
const AddSingleMandateResults = require('../containers/add-single-mandate-results-container');
const AddPartyResults = require('../containers/add-party-results-container');
//const AddRatingResults = require('../containers/add-rating-results-container');
const ResultsReport = require('../containers/results-report-container');

const Suvesti = require('../presentations/suvedete-balsus');
const Herb = require('../presentations/herb');

const ConstituenciesList = require('../containers/lists/constituencies-list-container');
const DistrictsList = require('../containers/lists/districts-list-container');
const PartiesList = require('../containers/lists/parties-list-container');
const RepresentativesList = require('../containers/lists/representatives-list-container');
const CandidatesList = require('../containers/lists/candidates-list-container');
const ActivityList = require('../containers/lists/activity-list-container');


const PublicResultsList = require('../presentations/lists/public-result-list-presentation');
const SingleMandateList = require('../containers/lists/single-mandate-results-container');

const SingleMandateDistrictsList = require('../presentations/lists/single-mandate-district-results-presentation');
const SingleMandateDistrictResultsList = require('../containers/lists/single-mandate-districts-list-container');

const routes = (
  <Router history={hashHistory}>
    <Route path="/" component={NavPublic}>
    	<IndexRoute component={Herb} />
    	<Route path="/results" component={PublicResultsList} >
        <IndexRoute component={SingleMandateList} />
        <Route path="/single-mandate" component={SingleMandateList} />
        <Route path="/single-mandate-districts/:constituencyId" component={SingleMandateDistrictResultsList} />
        <Route path="/single-mandate-district-results" component={SingleMandateDistrictsList} />
        <Route path="/multi-mandate" component={SingleMandateList} />
        <Route path="/activity" component={ActivityList} />
      </Route>
    </Route>

    <Route path="/login" component={Login}>
    </Route>

    <Route path="/admin" component={NavAdmin}>
      <IndexRoute component={ConstituenciesList} />
      <Route path="/admin/constituencies" component={ConstituenciesList} />
        <Route path="/admin/constituencies/add" component={AddConstituency} />
        <Route path="/admin/constituencies/edit/:constituencyId" component={AddConstituency} />

      <Route path="/admin/districts" component={DistrictsList} />
        <Route path="/admin/districts/list/:constituencyId" component={DistrictsList} />
        <Route path="/admin/districts/add" component={AddDistrict} />
        <Route path="/admin/districts/edit/:districtId" component={AddDistrict} />

      <Route path="/admin/representatives" component={RepresentativesList} />
        <Route path="/admin/representatives/add" component={AddRepresentative} />
        <Route path="/admin/representatives/edit/:representativeId" component={AddRepresentative} />
        <Route path="/admin/representatives/add/:districtId" component={AddRepresentative} />

      <Route path="/admin/parties" component={PartiesList} />
        <Route path="/admin/parties/add" component={AddParty} />
        <Route path="/admin/parties/add-list" component={AddConstituency} />
      <Route path="/admin/parties/edit/:partyId" component={AddParty} />

      <Route path="/admin/candidates" component={CandidatesList} />
        <Route path="/admin/candidates/add" component={AddCandidate} />
        <Route path="/admin/candidates/constituency/:constituencyId" component={CandidatesList} />
        <Route path="/admin/candidates/party/:partyId" component={CandidatesList} />
        <Route path="/admin/candidates/add-list" component={AddConstituency} />
      	<Route path="/admin/candidates/edit/:candidateId" component={AddCandidate} />
      </Route>

    <Route path="/representative" component={NavRepresentative}>
	    <IndexRoute component={AddSingleMandateResults} />
	    <Route path="/representative/results/single" component={AddSingleMandateResults} />
      <Route path="/representative/results/parties" component={AddPartyResults} />
      <Route path="/representative/results/report" component={ResultsReport} />
	  </Route>
  </Router>
);

module.exports = routes;
