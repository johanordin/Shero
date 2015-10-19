  /*
  The MIT License (MIT)
  
  Copyright (c) 2014 Gregory McGee
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
  */
  
  (function() {
    angular.module('gm.datepickerMultiSelect', ['ui.bootstrap'])
    .config(['$provide', '$injector', function($provide, $injector) {
      var delegate = function($delegate) {
        var directive = $delegate[0];
  
        /* Override compile */
        var link = directive.link;
  
        directive.compile = function() {
          return function(scope, element, attrs, ctrls) {
            link.apply(this, arguments);
  
            var selectedDates = [];
  
            /* Called when multiSelect model is updated */
            scope.$on('update', function(event, newDates) {
              selectedDates = newDates;
              update();
            });
  
            /* Get dates pushed into multiSelect array before Datepicker is ready */
            scope.$emit('requestSelectedDates');
  
            /*
              Fires when date is selected or when month is changed.
              UI bootstrap versions before 0.14.0 had just one controller DatepickerController,
              now they have UibDatepickerController, UibDaypickerController and DatepickerController
              see more on https://github.com/angular-ui/bootstrap/commit/44354f67e55c571df28b09e26a314a845a3b7397?diff=split#diff-6240fc17e068eaeef7095937a1d63eaeL251
              and https://github.com/angular-ui/bootstrap/commit/44354f67e55c571df28b09e26a314a845a3b7397?diff=split#diff-6240fc17e068eaeef7095937a1d63eaeR462
            */
            scope.$watch(function () {
              return angular.isArray(ctrls)
                ? ctrls[0].activeDate.getTime()
                : ctrls.activeDate.getTime();
            }, update);
  
            function update() {
              angular.forEach(scope.rows, function(row) {
                angular.forEach(row, function(day) {
                  day.selected = selectedDates.indexOf(day.date.setHours(0, 0, 0, 0)) > -1
                });
              });
            }
          }
        }
  
        return $delegate;
      }
      
      if ($injector.has('daypickerDirective'))
        $provide.decorator('daypickerDirective', ['$delegate', delegate]);
      
      if ($injector.has('uibDaypickerDirective'))
        $provide.decorator('uibDaypickerDirective', ['$delegate', delegate]);
    }])
    .directive('multiSelect', function() {
      return {
        require: ['ngModel'],
        link: function(scope, elem, attrs, ctrls) {
          var selectedDates;
          var selectRange;
  
          /* Called when directive is compiled */
          scope.$on('requestSelectedDates', function() {
            scope.$broadcast('update', selectedDates);
          });
  
          scope.$watchCollection(attrs.multiSelect, function(newVal) {
            selectedDates = newVal || [];
            scope.$broadcast('update', selectedDates);
          });
  
          attrs.$observe('selectRange', function(newVal) {
            selectRange = !!newVal && newVal !== "false";
          });
  
          scope.$watch(attrs.ngModel, function(newVal, oldVal) {
            if(!newVal) return;
  
            var dateVal = newVal.getTime();
  
            if(selectRange) {
              /* reset range */
              if(!selectedDates.length || selectedDates.length > 1 || selectedDates[0] == dateVal)
                return selectedDates.splice(0, selectedDates.length, dateVal);
  
              selectedDates.push(dateVal);
  
              var tempVal = Math.min.apply(null, selectedDates);
              var maxVal = Math.max.apply(null, selectedDates);
  
              /* Start on the next day to prevent duplicating the
                first date */
              tempVal = new Date(tempVal).setHours(24);
              while(tempVal < maxVal) {
                selectedDates.push(tempVal);
                /* Set a day ahead after pushing to prevent
                  duplicating last date */
                tempVal = new Date(tempVal).setHours(24);
              }
            } else {
              if(selectedDates.indexOf(dateVal) < 0) {
                selectedDates.push(dateVal);
              } else {
                selectedDates.splice(selectedDates.indexOf(dateVal), 1);
              }
            }
          });
        }
      }
    });
  })();
