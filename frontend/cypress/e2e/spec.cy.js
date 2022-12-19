describe('My First Test', () => {
  it('Visits page', () => {
    cy.visit('/')
  })
  it('Search flight', () => {
    cy.visit('/')
    cy.get('[data-cy="app-name"]').should('contain', 'FlyAway')
    cy.get('[data-cy="flyFrom-input"]').should('contain', '')
    cy.get('[data-cy="flyTo-input"]').type("London")
  })
})