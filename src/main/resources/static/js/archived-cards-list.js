
function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}


angular.module('kanbannow-archive-cards', []).controller('ArchivedCardListController', function($scope, $http) {

    //$scope.cardList = [{text: "Card1", date: "Date1"},  {text: "Card2", date: "Date2"} /*,*/];
    var getArchivedCardsUri = 'api/archived-cards';

    $scope.errorGettingArchivedCardList = false;
    $scope.errorGettingArchivedCardListErrorMessage = "";

    $http({
        method: 'GET',
        url: getArchivedCardsUri
    }).then(function successCallback(response) {
        $scope.cardList = response.data;
        sleep(5000)
    }, function errorCallback(response) {
        $scope.errorGettingArchivedCardList = true;
        $scope.errorGettingArchivedCardListErrorMessage = response.data;
    });

});


angular.module('ngTableTutorial', ['ngTable']).controller('tableController', function($scope, $filter, $http, ngTableParams) {

    var getArchivedCardsUri = 'api/archived-cards';

    $scope.errorGettingArchivedCardList = false;
    $scope.errorGettingArchivedCardListErrorMessage = "";

    $scope.usersTable = new ngTableParams({
        page: 1,
        count: 10
    }, {
        total: 0,
        getData: function ($defer, params) {


            $http({
                method: 'GET',
                url: getArchivedCardsUri,
                params: {pageNumber: params.page(), pageSize: params.count()}
            }).then(function successCallback(response) {
                $scope.users = response.data;
                $scope.data = $scope.users.slice((params.page() - 1) * params.count(), params.page() * params.count());
                params.total(response.data.length)

                Make rest call return total number of records too in addition
                to the paged records
                Then update params.total( response.data.totalNumRecords)
                or whatever structure I come up with for the return value of the rest call

                //sleep(1000)
                $defer.resolve($scope.data);
                //sleep(1000)
            }, function errorCallback(response) {
                $scope.errorGettingArchivedCardList = true;
                $scope.errorGettingArchivedCardListErrorMessage = response.data;
            });

        }
    });

});


