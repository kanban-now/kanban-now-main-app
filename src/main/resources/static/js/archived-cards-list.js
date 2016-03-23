
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

    var getArchivedCardsUri = 'api/new-archived-cards';

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
                $scope.data = response.data.data;
                params.total(response.data.pagingData.totalCount)

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


