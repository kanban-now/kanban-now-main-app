
angular.module('kanbannow-archive-cards', []).controller('ArchivedCardListController', function($scope, $http) {

    $scope.cardList = [{text: "Card1", date: "Date1"},  {text: "Card2", date: "Date2"} /*,*/];
    //var getArchivedCardsUri = 'api/archived-cards';
    //
    //$scope.errorGettingArchivedCardList = false;
    //$scope.errorGettingArchivedCardListErrorMessage = "";
    //window.getArchivedCardsLatch = 'started';
    //
    //$http({
    //    method: 'GET',
    //    url: getArchivedCardsUri
    //}).then(function successCallback(response) {
    //    $scope.cardList = response.data;
    //    window.getArchivedCardsLatch = 'done';
    //}, function errorCallback(response) {
    //    $scope.errorGettingArchivedCardList = true;
    //    $scope.errorGettingArchivedCardListErrorMessage = response.data;
    //    window.getArchivedCardsLatch = 'done';
    //});

});
