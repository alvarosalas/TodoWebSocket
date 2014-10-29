/*global $: false,*/
'use strict';
// var connection = 'http://api.beta2.se/abdi/';

angular.module('avaTodoApp')
	.controller('MainCtrl', function ($scope) {
		var webSocket = new WebSocket("ws://localhost:8080/postitWebsockets/websocketmain");
		webSocket.onopen = function (e) {
			console.log('Websocket online');
		}
		webSocket.onmessage = function (event) {
			console.log('Server sent: ' + event.data);
			var postit = JSON.parse(event.data);
			$scope.todos.push(postit);
			$scope.$apply();
		}
		webSocket.onerror = function (error) {
			console.log("error " + error);
		}
		webSocket.onclose = function (event) {
			console.log("Websocket closed");
		}
		$scope.todos = [];
		$scope.groupName = 'AvaPostIT';
		$scope.postitColor = 'Yellow';

		//Function som addar todos
		var newTodo;
		$scope.addTodo = function () {
			var uniqueId = new Date().getTime();
			newTodo = {
				id: uniqueId,
				text: $scope.todoTitle,
				descriptions: [],
				BGcolor: $scope.postitColor,
				type: 'add'
			};
			newTodo.descriptions.push($scope.todoTask);
			webSocket.send(JSON.stringify(newTodo));
			$scope.todoTitle = '';
			$scope.todoTask = '';
			console.log(newTodo);
		};

		//Function som addar flera task i en todo
		var addMoreTodo;
		$scope.addMoreTask = function () {
			addMoreTodo = {
				descriptions2: $scope.moreTodoTask
			};
			newTodo.descriptions.push(addMoreTodo.descriptions2);
			$scope.moreTodoTask = '';
			console.log("NEW TODO VÄRDE" + JSON.stringify($scope.todos));
			console.log("JSON STRINGIFY " + JSON.stringify(addMoreTodo));
			console.log("TODO DESC" + JSON.stringify(newTodo));
			webSocket.send(JSON.stringify(newTodo));
		};
		//Tar bort en todo från arrayen
		var removeTodo;
		$scope.removeTodo = function (start, todoId, todoText, todoDescriptions, todoBGcolor) {
			removeTodo = {
				id: todoId,
				text: todoText,
				descriptions: todoDescriptions,
				BGcolor: todoBGcolor,
				type: 'remove'
			};
			console.log(start);
			console.log(todoId + todoText + todoDescriptions + todoBGcolor);
			console.log($scope.todos)
			$scope.todos.splice(start, 1);
			console.log($scope.todos)
			webSocket.send(JSON.stringify(removeTodo));
		};

		$scope.color = {
			'Yellow': '../images/post-it.png',
			'Blue': '../images/post-it-blue.png',
			'Orange': '../images/post-it-orange.png',
			'Green': '../images/post-it-green.png',
			'Pink': '../images/post-it-pink.png'
		};
		var editTitle;
		$scope.editTitle = function (todo, editedTitle) {
			editTitle = {
				BGcolor: todo.BGcolor,
				text: todo.text,
				descriptions: todo.descriptions,
				id: todo.id,
				type: 'editTitle'
			};
			todo.text = editedTitle;
			console.log(editTitle);
			webSocket.send(JSON.stringify(editTitle));
		};

		var editTask;
		$scope.editTask = function (taskId, todo, editedTask) {
			console.log(todo.descriptions);
			console.log(todo.id);
			editTask = {
				id: todo.id,
				text: todo.text,
				descriptions: todo.descriptions,
				BGcolor: todo.BGcolor,
				type: 'editTask'
			};
			todo.descriptions[taskId] = editedTask;
			console.log(JSON.stringify(editTask));
			webSocket.send(JSON.stringify(editTask));

		};
	});
