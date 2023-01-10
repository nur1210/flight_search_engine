describe('My First Test', () => {
  it('Search flight', () => {
    cy.visit('/')
    cy.get('[data-cy="app-name"]').should('contain', 'FlyAway')
    cy.get('[data-cy="flyFrom-input"]').should('contain', '')
    cy.get('[data-cy="flyFrom-input"]').type('Amster')
    cy.get(':nth-child(9) > :nth-child(1)').click()
    cy.get('[data-cy="flyTo-input"]').should('contain', '')
    cy.get('[data-cy="flyTo-input"]').type("Tel a")
    cy.get(':nth-child(14) > :nth-child(1)').click();
    cy.get(':nth-child(3) > .MuiInputBase-root > .MuiInputBase-input').type('2023-02-28')
    cy.get(':nth-child(4) > .MuiInputBase-root').type('2023-03-03')
    cy.get('.MuiBox-root > .MuiButtonBase-root').click()
    cy.get('.css-widufq > .MuiTypography-root').should('contain', 'Search Results')
    cy.get('.css-tpvvv8 > .MuiContainer-root').should('contain', '€')
  })

  it('', () => {
    cy.visit('/')
    cy.get('[data-cy="app-name"]').should('contain', 'FlyAway')
    cy.get('[data-cy="flyFrom-input"]').should('contain', '')
    cy.get('[data-cy="flyFrom-input"]').type('Amster')
    cy.get(':nth-child(9) > :nth-child(1)').click()
    cy.get('[data-cy="flyTo-input"]').should('contain', '')
    cy.get('[data-cy="flyTo-input"]').type("Tel a")
    cy.get(':nth-child(14) > :nth-child(1)').click();
    cy.get(':nth-child(3) > .MuiInputBase-root > .MuiInputBase-input').type('2023-02-28')
    cy.get(':nth-child(4) > .MuiInputBase-root').type('2023-03-03')
    cy.get('.MuiBox-root > .MuiButtonBase-root').click()
    cy.get('.css-widufq > .MuiTypography-root').should('contain', 'Search Results')
    cy.get('.css-tpvvv8 > .MuiContainer-root').should('contain', '€')
  })
})