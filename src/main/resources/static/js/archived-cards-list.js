




function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}


function addDivToBody(divId) {
    var myEl = angular.element( document.querySelector( 'body' ) );
    var divContents = '<div id="' + divId + '"></div>';
    myEl.append(divContents);

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
        addDivToBody('ajaxCompleted');
    }, function errorCallback(response) {
        $scope.errorGettingArchivedCardList = true;
        $scope.errorGettingArchivedCardListErrorMessage = response.data;
        addDivToBody('ajaxCompleted');
    });

});
