angular.module('SHeroApp')
.controller('DatePickerCtrl', function() {
  this.activeDate = null;
  this.selectedDates = [new Date().setHours(0, 0, 0, 0)];
  this.type = 'individual';
  
  this.removeFromSelected = function(dt) {
    this.selectedDates.splice(this.selectedDates.indexOf(dt), 1);
  }
})