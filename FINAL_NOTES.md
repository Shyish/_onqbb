* Since I didn't have strict time restrictions I decided to play a bit with RxJava2,
Kotlin and Dagger2, hope that's ok. Unfortunately I wasn't totally confident with Dagger2
(now I am way more :)) so it took me a while to make it work with Kotlin.

* Used RxJava2 for it's simplicity and readability + is really easy to change the thread in
which a piece of code is executed* Used Kotlin for it's simplicity (just take a look at all
the POJOs) and easiness to read. I'm more confident with Java but for small scenarios like
this one it is a good investment in my opinion.

* MVPish: I used an MVP approach with some touches of reactive view, but didn't wanted to add
more complexity by letting the view expose observables to it's widgets, but that could be
improved.

* Tests: I decided not to test the data sources since we're not making any request but using
local data, which means that the tests would be pretty verbose and they would just check that
moshi works as expected (so the ROI would be pretty low if not negative).
For presenters I normally add tests since the code is meant to grow at some point, but for this
scenario it doesn't make much sense I think.
For interactors I added couple of small tests to test empty states and the most important
features, but they could be covered more in depth with no big effort.

* To avoid extra code and states and since the design wasn't a priority I decided to lock
the orientation so every time the activity is opened all the data is loaded again. This is
easily fixed by just saving/restoring the state and passing it to the presenter.

* Algorithm: For this part I decided to go with Dijkstra. The reason for that is that there
are at least 2 possible ways: shortest path and optimal path. If we consider a scenario where
there are no penalties for currency conversion, then the optimal path will deliver better
conversions. On the other hand if we would consider some extra penalties or even not caring
about that we could simply choose the simplest algorithm, which could be a Breadth-first
search. In any case changing the implementation should be pretty easy by just implementing the
GraphPathAlgorithm contract.

Since I didn't want to reinvent the wheel by doing a custom implementation of the algorithm
I searched and took the one stated here (but honestly the test in the article is basically
doing nothing, so I modified it a bit on my own):
http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
*to respect the original source I left that class in Java, but the conversion to Kotlin
would be pretty straightforward.

I've also modified the algorithm a bit. The idea is that we want to calculate the optimal
path between 2 conversions, so the initial approach is to make the weights of the edges
to be the conversion rate, but:

    - In our case the path would be traversed by multiplying the original quantities by the
    conversion rate, which means that we cannot just SUM the weights as done in the original
    class, so we need to multiply them.
    - For Dijkstra the lower the weight the better the path, but for us is the opposite:
    the lower the conversion, the worse, so in the RatesToGraphConverter I inverted the
    conversion rate and then instead of multiply the quantity and the distance we just
    divide. Another option would have been to just negate the conversion rate and then
    to a abs on the result (but I would need more time to properly benchmark the memory/cpu
    consumption for this).

As stated in the "TODO" of the TransactionInteractor, a possible improvement would be to
introduce a memory or disk cache so we don't have to recalculate already known paths for
the same rates.

* Timing: To be honest I dedicated more time to the overall architecture than the algorithm
itself since I wanted to try to mix Kotlin + Dagger + RxJava and I've never tried before, so
I think I spend around 3-4h.
That said the algorithm part took me maybe 1.5-2h but most of the time invested there was
thinking and drawing to properly understand the problem.
