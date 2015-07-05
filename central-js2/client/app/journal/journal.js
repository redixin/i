angular.module('journal').config(function($stateProvider) {

  $stateProvider
    .state('index.journal', {
      url: '/journal',
      views: {
        '': {
          templateUrl: 'app/journal/index.html',
          controller: 'JournalController'
        }
      }
    })
    .state('index.journal.bankid', {
      url: '/bankid?code&error',
      parent: 'index.journal',
      views: {
        'bankid': {
          templateUrl: 'app/journal/bankid/index.html',
          controller: 'JournalBankIdController'
        }
      }
    })
    .state('index.journal.content', {
      url: '/content?code',
      parent: 'index.journal',
      resolve: {
        BankIDLogin: function($q, $state, $location, $stateParams, BankIDService) {
          return BankIDService.isLoggedIn().then(function() {
            return {loggedIn: true};
          }).catch(function() {
            return $q.reject(null);
          });
        },
        BankIDAccount: function(BankIDService) {
          return BankIDService.account();
        },
        customer: function(BankIDAccount) {
          return BankIDAccount.customer;
        },
        journal: function($q, $state, ServiceService) {
          return ServiceService.getJournalEvents();
        }
      },
      views: {
        'content': {
          templateUrl: 'app/journal/content.html',
          controller: 'JournalContentController'
        }
      }
    });
})
;

